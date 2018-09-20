# poc-mongodb-cosmosdb

[![Build Status](https://travis-ci.org/nortthon/poc-mongodb-cosmosdb.svg?branch=master)](https://travis-ci.org/nortthon/poc-mongodb-cosmosdb)

#### Create Index using expiration parameter
`db.getCollection("user").createIndex({ "createdDate": 1 }, { "expireAfterSeconds": 300 })`
