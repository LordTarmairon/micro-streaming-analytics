db = db.getSiblingDB('AMPLIA_DB');
db.createUser({
  user: 'ampliadb',
  pwd: 'ampliadbtask123.',
  roles: [
    { role: 'readWrite', db: 'AMPLIA_DB' }
  ]
});
