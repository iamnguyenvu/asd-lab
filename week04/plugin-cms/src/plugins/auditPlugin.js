const { query } = require("../config/database");

module.exports = {
  name: "audit-plugin",
  description: "Store content lifecycle events for auditability",
  hooks: {
    async afterCreate(context) {
      await query(
        "INSERT INTO audit_logs (event_type, content_id, payload) VALUES ($1, $2, $3::jsonb)",
        ["CONTENT_CREATED", context.content.id, JSON.stringify({ title: context.content.title })]
      );
    },

    async afterPublish(context) {
      await query(
        "INSERT INTO audit_logs (event_type, content_id, payload) VALUES ($1, $2, $3::jsonb)",
        ["CONTENT_PUBLISHED", context.content.id, JSON.stringify({ slug: context.content.slug })]
      );
    },

    async afterDelete(context) {
      await query(
        "INSERT INTO audit_logs (event_type, content_id, payload) VALUES ($1, $2, $3::jsonb)",
        ["CONTENT_DELETED", context.contentId, JSON.stringify({ deletedBy: "editor" })]
      );
    },
  },
};
