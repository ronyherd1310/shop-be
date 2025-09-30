# PostgreSQL Database Setup

This document provides instructions for setting up PostgreSQL database connectivity for the Shop Backend application.

## Prerequisites

- PostgreSQL server installed and running
- Java 17 or higher
- Maven 3.6 or higher

## Database Setup

### 1. Install PostgreSQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

**macOS (using Homebrew):**
```bash
brew install postgresql
brew services start postgresql
```

**Windows:**
Download and install from [PostgreSQL official website](https://www.postgresql.org/download/windows/)

### 2. Create Database and User

Connect to PostgreSQL as the postgres user:
```bash
sudo -u postgres psql
```

Create the database, user, and grant permissions:
```sql
-- Create database
CREATE DATABASE shopdb;

-- Create user
CREATE USER shopuser WITH PASSWORD 'shoppass';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE shopdb TO shopuser;
GRANT ALL ON SCHEMA public TO shopuser;

-- Exit
\q
```

### 3. Configuration

The application is configured to use PostgreSQL by default. You can modify the database connection settings in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/shopdb
spring.datasource.username=shopuser
spring.datasource.password=shoppass
spring.datasource.driver-class-name=org.postgresql.Driver

# Repository Configuration
app.repository.type=jpa
```

### 4. Repository Mode Switching

You can switch between PostgreSQL and in-memory storage by changing the `app.repository.type` property:

- **PostgreSQL (JPA):** `app.repository.type=jpa` (default)
- **In-Memory:** `app.repository.type=memory`

## Running the Application

### With PostgreSQL (default)
```bash
# Ensure PostgreSQL is running
sudo service postgresql start  # Linux
brew services start postgresql  # macOS

# Run the application
mvn spring-boot:run
```

### With In-Memory Storage
```bash
# Temporarily override the repository type
mvn spring-boot:run -Dapp.repository.type=memory
```

Or modify `application.properties`:
```properties
app.repository.type=memory
```

## Database Schema

The application automatically creates the following tables:

- **products**: Product catalog with id, name, description, and price
- **orders**: Customer orders with order details and status
- **order_items**: Individual items within orders

The schema is managed by Hibernate with `spring.jpa.hibernate.ddl-auto=update` setting, which automatically creates and updates tables based on entity annotations.

## Sample Data

The application automatically populates the database with sample data on first run:
- 10 sample products (laptops, headphones, smartphones, etc.)
- 5 sample orders with various items

This initialization only occurs when the database is empty.

## Development Tips

1. **Database Debugging**: Enable SQL logging with `spring.jpa.show-sql=true`
2. **Schema Management**: Use `spring.jpa.hibernate.ddl-auto=create-drop` for testing (recreates schema on each run)
3. **Connection Pooling**: Configured with HikariCP (max 20 connections, min 5 idle)

## Troubleshooting

### Connection Issues
- Verify PostgreSQL is running: `sudo service postgresql status`
- Check if the database exists: `psql -U shopuser -d shopdb -h localhost`
- Ensure firewall allows connections on port 5432

### Permission Issues
```sql
-- Grant additional permissions if needed
GRANT ALL ON ALL TABLES IN SCHEMA public TO shopuser;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO shopuser;
```

### Reset Database
```sql
-- Drop and recreate database
DROP DATABASE shopdb;
CREATE DATABASE shopdb;
GRANT ALL PRIVILEGES ON DATABASE shopdb TO shopuser;
```