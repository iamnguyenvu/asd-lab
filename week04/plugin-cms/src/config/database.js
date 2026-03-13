const { Pool } = require("pg");

const databaseUrl = process.env.DATABASE_URL;

if (!databaseUrl) {
  throw new Error("DATABASE_URL is required");
}

const pool = new Pool({
  connectionString: databaseUrl,
});

async function query(text, params = []) {
  return pool.query(text, params);
}

async function initializeDatabase() {
  await query(`
    CREATE TABLE IF NOT EXISTS contents (
      id SERIAL PRIMARY KEY,
      title VARCHAR(180) NOT NULL,
      slug VARCHAR(200) NOT NULL UNIQUE,
      body TEXT NOT NULL,
      status VARCHAR(20) NOT NULL DEFAULT 'draft',
      metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
      created_at TIMESTAMP NOT NULL DEFAULT NOW(),
      updated_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
  `);

  await query(`
    CREATE TABLE IF NOT EXISTS audit_logs (
      id SERIAL PRIMARY KEY,
      event_type VARCHAR(100) NOT NULL,
      content_id INTEGER,
      payload JSONB NOT NULL DEFAULT '{}'::jsonb,
      created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
  `);

  await query(`
    CREATE TABLE IF NOT EXISTS plugin_states (
      plugin_name VARCHAR(120) PRIMARY KEY,
      enabled BOOLEAN NOT NULL DEFAULT TRUE,
      updated_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
  `);

  await query(`
    CREATE INDEX IF NOT EXISTS idx_contents_status_updated_at
    ON contents (status, updated_at DESC);
  `);

  await query(`
    CREATE INDEX IF NOT EXISTS idx_audit_logs_content_created_at
    ON audit_logs (content_id, created_at DESC);
  `);
}

module.exports = {
  pool,
  query,
  initializeDatabase,
};
