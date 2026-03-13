function createContentController(contentService, pluginManager) {
  return {
    listContents: async (req, res, next) => {
      try {
        const contents = await contentService.list(req.query.status);
        res.json({ success: true, data: contents });
      } catch (error) {
        next(error);
      }
    },

    getContent: async (req, res, next) => {
      try {
        const content = await contentService.getById(Number(req.params.id));
        if (!content) {
          return res.status(404).json({ success: false, message: "Content not found" });
        }

        return res.json({ success: true, data: content });
      } catch (error) {
        return next(error);
      }
    },

    createContent: async (req, res, next) => {
      try {
        const content = await contentService.create(req.body);
        res.status(201).json({ success: true, data: content });
      } catch (error) {
        if (error.statusCode === 400) {
          return res.status(400).json({ success: false, message: error.message });
        }

        if (String(error.message).includes("duplicate key")) {
          return res.status(409).json({ success: false, message: "Slug already exists" });
        }

        return next(error);
      }
    },

    updateContent: async (req, res, next) => {
      try {
        const content = await contentService.update(Number(req.params.id), req.body);

        if (!content) {
          return res.status(404).json({ success: false, message: "Content not found" });
        }

        return res.json({ success: true, data: content });
      } catch (error) {
        if (error.statusCode === 400) {
          return res.status(400).json({ success: false, message: error.message });
        }

        if (String(error.message).includes("duplicate key")) {
          return res.status(409).json({ success: false, message: "Slug already exists" });
        }

        return next(error);
      }
    },

    publishContent: async (req, res, next) => {
      try {
        const content = await contentService.publish(Number(req.params.id));

        if (!content) {
          return res.status(404).json({ success: false, message: "Content not found" });
        }

        return res.json({ success: true, data: content });
      } catch (error) {
        return next(error);
      }
    },

    deleteContent: async (req, res, next) => {
      try {
        const deleted = await contentService.remove(Number(req.params.id));

        if (!deleted) {
          return res.status(404).json({ success: false, message: "Content not found" });
        }

        return res.json({ success: true, message: "Content deleted" });
      } catch (error) {
        return next(error);
      }
    },

    listPlugins: async (_req, res, next) => {
      try {
        return res.json({ success: true, data: pluginManager.list() });
      } catch (error) {
        return next(error);
      }
    },

    togglePlugin: async (req, res, next) => {
      try {
        const enabled = Boolean(req.body.enabled);
        const result = await pluginManager.setEnabled(req.params.name, enabled);

        if (!result) {
          return res.status(404).json({ success: false, message: "Plugin not found" });
        }

        return res.json({ success: true, data: result });
      } catch (error) {
        return next(error);
      }
    },
  };
}

module.exports = {
  createContentController,
};
