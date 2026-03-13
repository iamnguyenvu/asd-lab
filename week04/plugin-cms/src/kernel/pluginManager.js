const { query } = require("../config/database");

class PluginManager {
  constructor() {
    this.plugins = [];
    this.state = new Map();
  }

  register(plugin) {
    if (!plugin || !plugin.name) {
      throw new Error("Plugin must have a name");
    }

    this.plugins.push(plugin);
    this.state.set(plugin.name, true);
  }

  async syncStateFromDatabase() {
    const result = await query("SELECT plugin_name, enabled FROM plugin_states");

    for (const row of result.rows) {
      this.state.set(row.plugin_name, row.enabled);
    }

    for (const plugin of this.plugins) {
      await query(
        `
          INSERT INTO plugin_states (plugin_name, enabled)
          VALUES ($1, $2)
          ON CONFLICT (plugin_name)
          DO NOTHING
        `,
        [plugin.name, this.state.get(plugin.name) ?? true]
      );
    }
  }

  list() {
    return this.plugins.map((plugin) => ({
      name: plugin.name,
      description: plugin.description,
      enabled: this.state.get(plugin.name) ?? true,
      hooks: Object.keys(plugin.hooks || {}),
    }));
  }

  async setEnabled(pluginName, enabled) {
    const plugin = this.plugins.find((item) => item.name === pluginName);

    if (!plugin) {
      return null;
    }

    this.state.set(pluginName, enabled);

    await query(
      `
        INSERT INTO plugin_states (plugin_name, enabled)
        VALUES ($1, $2)
        ON CONFLICT (plugin_name)
        DO UPDATE SET enabled = EXCLUDED.enabled, updated_at = NOW()
      `,
      [pluginName, enabled]
    );

    return {
      name: plugin.name,
      enabled,
    };
  }

  async runHook(hookName, context) {
    for (const plugin of this.plugins) {
      const isEnabled = this.state.get(plugin.name) ?? true;
      if (!isEnabled) {
        continue;
      }

      const hook = plugin.hooks?.[hookName];
      if (typeof hook === "function") {
        await hook(context);
      }
    }
  }
}

module.exports = {
  PluginManager,
};
