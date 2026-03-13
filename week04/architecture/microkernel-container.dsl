workspace "Plugin CMS - Microkernel Architecture" "C4 Container view for microkernel architecture" {
  model {
    editor = person "Content Editor" "Creates and publishes content"
    admin = person "System Admin" "Controls plugins"

    cms = softwareSystem "Plugin-based CMS" "Microkernel CMS" {
      frontend = container "CMS Web UI" "CMS dashboard for editors and admins" "HTML/CSS/JavaScript"
      kernelApi = container "CMS Core Backend (Microkernel)" "Core services + plugin runtime (seo-plugin, audit-plugin) loaded in-process via plugin manager and hooks" "Node.js + Express"
      db = container "PostgreSQL" "Stores contents, plugin states, and audit logs" "PostgreSQL"
    }

    editor -> frontend "Creates and publishes content"
    admin -> frontend "Enable/disable plugins"

    frontend -> kernelApi "REST/JSON"
    kernelApi -> db "Read/write content, plugin state, and audit data"
  }

  views {
    container cms {
      include *
      autolayout tb
      title "Microkernel Architecture - Container View"
    }

    theme default
  }
}
