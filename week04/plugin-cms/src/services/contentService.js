const slugify = require("slugify");
const repository = require("../repositories/contentRepository");

class ContentService {
  constructor(pluginManager) {
    this.pluginManager = pluginManager;
  }

  async list(status) {
    return repository.listContents(status);
  }

  async getById(id) {
    return repository.getContentById(id);
  }

  async create(payload) {
    const sanitized = this.#sanitizePayload(payload, "draft");
    await this.pluginManager.runHook("beforeCreate", { payload: sanitized });

    const content = await repository.createContent(sanitized);

    await this.pluginManager.runHook("afterCreate", { content });
    return content;
  }

  async update(id, payload) {
    const existing = await repository.getContentById(id);
    if (!existing) {
      return null;
    }

    const sanitized = this.#sanitizePayload(
      {
        ...existing,
        ...payload,
      },
      existing.status
    );

    await this.pluginManager.runHook("beforeUpdate", { id, payload: sanitized, existing });

    const content = await repository.updateContent(id, sanitized);

    await this.pluginManager.runHook("afterUpdate", { content });
    return content;
  }

  async publish(id) {
    const content = await repository.publishContent(id);
    if (!content) {
      return null;
    }

    await this.pluginManager.runHook("afterPublish", { content });
    return content;
  }

  async remove(id) {
    const deleted = await repository.deleteContent(id);

    if (deleted) {
      await this.pluginManager.runHook("afterDelete", { contentId: id });
    }

    return deleted;
  }

  #sanitizePayload(payload, fallbackStatus) {
    const status = payload.status === "published" ? "published" : fallbackStatus;

    const title = String(payload.title || "").trim();
    const body = String(payload.body || "").trim();
    const slug = this.#ensureSlug(payload.slug, payload.title);

    if (!title || title.length < 3) {
      const error = new Error("Title must be at least 3 characters");
      error.statusCode = 400;
      throw error;
    }

    if (!body || body.length < 20) {
      const error = new Error("Body must be at least 20 characters");
      error.statusCode = 400;
      throw error;
    }

    if (!slug) {
      const error = new Error("A valid slug or title is required");
      error.statusCode = 400;
      throw error;
    }

    return {
      title,
      slug,
      body,
      status,
      metadata: payload.metadata || {},
    };
  }

  #ensureSlug(slug, title) {
    const raw = String(slug || title || "").trim();

    return slugify(raw, {
      lower: true,
      strict: true,
    });
  }
}

module.exports = {
  ContentService,
};
