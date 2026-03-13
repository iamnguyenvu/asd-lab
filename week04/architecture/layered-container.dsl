workspace "Plugin CMS - Layered Architecture" "C4 Container view with explicit standard layers" {
  model {
    editor = person "Content Editor" "Creates and publishes content"
    admin = person "System Admin" "Manages plugins"

    cms = softwareSystem "Plugin-based CMS" "CMS designed using layered architecture" {
      presentationLayer = container "Presentation Layer" "Web UI and HTTP controllers for editor/admin interactions" "HTML/CSS/JavaScript + Express Controllers"
      businessLayer = container "Business Layer" "Content lifecycle rules, publishing workflow, and plugin orchestration" "Node.js Services"
      dataAccessLayer = container "Data Access Layer" "Repositories and database access logic" "Node.js + pg"
      database = container "PostgreSQL" "Stores content, plugin state, and audit logs" "PostgreSQL"
    }

    editor -> presentationLayer "Creates and publishes content"
    admin -> presentationLayer "Enables/disables plugins"
    presentationLayer -> businessLayer "Invokes use cases"
    businessLayer -> dataAccessLayer "Requests persistence"
    dataAccessLayer -> database "Reads/writes via SQL"
  }

  views {
    container cms {
      include *
      autolayout tb
      title "Layered Architecture - C4 Container View"
    }

    theme default
  }
}
