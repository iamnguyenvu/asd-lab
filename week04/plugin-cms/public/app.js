const contentForm = document.getElementById("content-form");
const contentList = document.getElementById("content-list");
const pluginList = document.getElementById("plugins");

document.getElementById("refresh-contents").addEventListener("click", loadContents);
document.getElementById("refresh-plugins").addEventListener("click", loadPlugins);

contentForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const payload = {
    title: document.getElementById("title").value,
    slug: document.getElementById("slug").value,
    body: document.getElementById("body").value,
  };

  const response = await fetch("/api/contents", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    const error = await response.json();
    alert(error.message || "Failed to create content");
    return;
  }

  contentForm.reset();
  await loadContents();
});

async function loadContents() {
  const response = await fetch("/api/contents");
  const payload = await response.json();
  contentList.innerHTML = "";

  for (const item of payload.data || []) {
    const card = document.createElement("article");
    card.className = "card";

    const metadata = item.metadata || {};

    card.innerHTML = `
      <h3>${item.title}</h3>
      <div class="meta">/${item.slug} | ${item.status}</div>
      <div>${item.body.slice(0, 120)}</div>
      <div class="meta">SEO: ${metadata.seoTitle || "n/a"} | score ${metadata.readabilityScore || "n/a"}</div>
      <div class="row">
        <button data-action="publish" data-id="${item.id}">Publish</button>
        <button class="secondary" data-action="delete" data-id="${item.id}">Delete</button>
      </div>
    `;

    contentList.appendChild(card);
  }

  bindContentActions();
}

function bindContentActions() {
  for (const button of contentList.querySelectorAll("button[data-action]")) {
    button.addEventListener("click", async () => {
      const id = button.dataset.id;
      const action = button.dataset.action;

      if (action === "publish") {
        await fetch(`/api/contents/${id}/publish`, { method: "POST" });
      }

      if (action === "delete") {
        await fetch(`/api/contents/${id}`, { method: "DELETE" });
      }

      await loadContents();
    });
  }
}

async function loadPlugins() {
  const response = await fetch("/api/plugins");
  const payload = await response.json();

  pluginList.innerHTML = "";

  for (const plugin of payload.data || []) {
    const card = document.createElement("article");
    card.className = "card";

    const buttonLabel = plugin.enabled ? "Disable" : "Enable";

    card.innerHTML = `
      <h3>${plugin.name}</h3>
      <div>${plugin.description}</div>
      <div class="meta">Hooks: ${plugin.hooks.join(", ") || "none"}</div>
      <div class="meta">Status: ${plugin.enabled ? "enabled" : "disabled"}</div>
      <button data-name="${plugin.name}">${buttonLabel}</button>
    `;

    const button = card.querySelector("button");
    button.addEventListener("click", async () => {
      await fetch(`/api/plugins/${plugin.name}/toggle`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ enabled: !plugin.enabled }),
      });

      await loadPlugins();
    });

    pluginList.appendChild(card);
  }
}

loadContents();
loadPlugins();
