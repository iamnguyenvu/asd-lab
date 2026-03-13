workspace "Plugin CMS - Microkernel Architecture" "C4 Container view with explicit microkernel elements" {
  model {
    editor = person "Content Editor" "Creates and publishes content"
    admin = person "System Admin" "Controls plugins"

    cms = softwareSystem "Plugin-based CMS" "CMS designed with microkernel architecture" {
      frontend = container "CMS Web UI" "Dashboard for content editing and plugin administration" "HTML/CSS/JavaScript"
      kernel = container "Microkernel Core" "Core CMS services: content lifecycle, publishing, plugin manager, hook engine" "Node.js + Express"
      seoPlugin = container "SEO Plugin" "Extension plugin for SEO metadata enrichment" "Node.js Plugin"
      auditPlugin = container "Audit Plugin" "Extension plugin for lifecycle audit logging" "Node.js Plugin"
      db = container "PostgreSQL" "Stores contents, plugin states, and audit logs" "PostgreSQL"
    }

    editor -> frontend "Creates and publishes content"
    admin -> frontend "Enable/disable plugins"

    frontend -> kernel "Uses REST/JSON"
    kernel -> seoPlugin "Calls plugin hooks"
    kernel -> auditPlugin "Calls plugin hooks"
    kernel -> db "Reads/writes content and plugin state"
    auditPlugin -> db "Writes audit events"
  }

  views {
    container cms {
      include *
      autolayout tb
      title "Microkernel Architecture - C4 Container View"
    }

    theme default
  }
}
