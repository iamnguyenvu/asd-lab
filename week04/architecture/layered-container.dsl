workspace "Plugin CMS - Layered Architecture" "C4 Container view for layered architecture" {
  model {
    editor = person "Content Editor" "Creates and publishes content"
    admin = person "System Admin" "Manages plugins"

    cms = softwareSystem "Plugin-based CMS" "Content platform with layered architecture" {
      webUi = container "CMS Web UI" "Browser-based dashboard for content and plugin management" "HTML/CSS/JavaScript"
      cmsApi = container "CMS Backend API" "Implements layered design internally: presentation, application, domain, infrastructure" "Node.js + Express"
      database = container "PostgreSQL" "Stores contents, plugin states, and audit logs" "PostgreSQL"
    }

    editor -> webUi "Creates and publishes content"
    admin -> webUi "Enables/disables plugins"
    webUi -> cmsApi "Uses REST/JSON"
    cmsApi -> database "Reads and writes data via SQL"
  }

  views {
    container cms {
      include *
      autolayout tb
      title "Layered Architecture - Container View"
    }

    theme default
  }
}
