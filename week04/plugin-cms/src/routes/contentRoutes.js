const { Router } = require("express");

function createContentRoutes(controller) {
  const router = Router();

  router.get("/contents", controller.listContents);
  router.get("/contents/:id", controller.getContent);
  router.post("/contents", controller.createContent);
  router.put("/contents/:id", controller.updateContent);
  router.post("/contents/:id/publish", controller.publishContent);
  router.delete("/contents/:id", controller.deleteContent);

  router.get("/plugins", controller.listPlugins);
  router.patch("/plugins/:name/toggle", controller.togglePlugin);

  return router;
}

module.exports = {
  createContentRoutes,
};
