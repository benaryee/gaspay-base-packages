#!/bin/bash
mongo -u root -p root --authenticationDatabase admin chap --eval "db.createUser({ user: 'product', pwd: 'password', roles: [{ role: 'dbOwner', db: 'product-service' }] })"
mongo -u root -p root --authenticationDatabase admin chap  --eval "db.createUser({ user: 'ussd', pwd: 'password', roles: [{ role: 'dbOwner', db: 'ussd' }] })"
mongo -u root -p root --authenticationDatabase admin chap --eval "db.createUser({ user: 'order', pwd: 'password', roles: [{ role: 'dbOwner', db: 'order-service' }] })"
mongo -u root -p root --authenticationDatabase admin chap --eval "db.createUser({ user: 'auth', pwd: 'password', roles: [{ role: 'dbOwner', db: 'auth' }] })"
mongo -u root -p root --authenticationDatabase admin chap --eval "db.createUser({ user: 'payment', pwd: 'password', roles: [{ role: 'dbOwner', db: 'payment-service' }] })"