#!/bin/bash

echo "Resetting the database..."
PGPASSWORD=postgres psql -U postgres -h localhost -d postgres -f sql/reset_database.sql

echo "Setting up the database with new schema..."
PGPASSWORD=postgres psql -U postgres -h localhost -d postgres -f sql/setup_database.sql

echo "Database reset and setup complete. Now running the application..."
./compile_and_run.sh 