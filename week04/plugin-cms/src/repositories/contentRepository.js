const { query } = require("../config/database");

async function listContents(status) {
  const values = [];
  let sql = "SELECT * FROM contents";

  if (status) {
    values.push(status);
    sql += ` WHERE status = $${values.length}`;
  }

  sql += " ORDER BY updated_at DESC";

  const result = await query(sql, values);
  return result.rows;
}

async function getContentById(id) {
  const result = await query("SELECT * FROM contents WHERE id = $1", [id]);
  return result.rows[0] || null;
}

async function createContent(payload) {
  const result = await query(
    `
      INSERT INTO contents (title, slug, body, status, metadata)
      VALUES ($1, $2, $3, $4, $5::jsonb)
      RETURNING *
    `,
    [payload.title, payload.slug, payload.body, payload.status, JSON.stringify(payload.metadata || {})]
  );

  return result.rows[0];
}

async function updateContent(id, payload) {
  const result = await query(
    `
      UPDATE contents
      SET title = $1,
          slug = $2,
          body = $3,
          metadata = $4::jsonb,
          updated_at = NOW()
      WHERE id = $5
      RETURNING *
    `,
    [payload.title, payload.slug, payload.body, JSON.stringify(payload.metadata || {}), id]
  );

  return result.rows[0] || null;
}

async function publishContent(id) {
  const result = await query(
    `
      UPDATE contents
      SET status = 'published',
          updated_at = NOW()
      WHERE id = $1
      RETURNING *
    `,
    [id]
  );

  return result.rows[0] || null;
}

async function deleteContent(id) {
  const result = await query("DELETE FROM contents WHERE id = $1 RETURNING id", [id]);
  return Boolean(result.rowCount);
}

module.exports = {
  listContents,
  getContentById,
  createContent,
  updateContent,
  publishContent,
  deleteContent,
};
