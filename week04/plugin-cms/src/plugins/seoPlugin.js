function computeSeoHints(content) {
  const plainText = content.body.replace(/\s+/g, " ").trim();
  const summary = plainText.slice(0, 140);

  return {
    seoTitle: content.title.slice(0, 60),
    seoDescription: summary,
    readabilityScore: Math.max(20, 100 - Math.floor(plainText.length / 40)),
  };
}

module.exports = {
  name: "seo-plugin",
  description: "Auto-generate SEO metadata for each content entry",
  hooks: {
    async beforeCreate(context) {
      const seoHints = computeSeoHints(context.payload);
      context.payload.metadata = {
        ...(context.payload.metadata || {}),
        ...seoHints,
      };
    },

    async beforeUpdate(context) {
      const seoHints = computeSeoHints(context.payload);
      context.payload.metadata = {
        ...(context.payload.metadata || {}),
        ...seoHints,
      };
    },
  },
};
