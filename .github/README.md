# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the Restaurant Service project.

## Workflows

### 1. CI/CD Pipeline (`ci-cd.yml`)
**Triggers:** Push to `main` or `develop` branches, Pull requests to `main` or `develop`

**Jobs:**
- **Test**: Runs unit tests with MySQL database, generates coverage reports
- **Build**: Compiles and packages the application
- **Docker**: Builds and pushes Docker image (only on main branch)
- **Security Scan**: Runs OWASP dependency check for security vulnerabilities

**Features:**
- Maven dependency caching for faster builds
- JaCoCo code coverage reporting
- Codecov integration for coverage tracking
- Test result publishing
- Docker image building and pushing
- Security vulnerability scanning

### 2. Pull Request Validation (`pr-validation.yml`)
**Triggers:** Pull requests to `main` or `develop` branches

**Features:**
- Code compilation validation
- Test execution with coverage
- Coverage threshold checking (70% overall, 80% for changed files)
- Automatic PR comments with coverage reports
- Build validation

### 3. Dependency Update Check (`dependency-update.yml`)
**Triggers:** Weekly schedule (Mondays at 2 AM UTC), Manual trigger

**Features:**
- Checks for outdated dependencies
- Checks for outdated Maven plugins
- Generates dependency tree report
- Uploads reports as artifacts

## Setup Requirements

### Repository Secrets
Add these secrets to your GitHub repository:

1. **DOCKER_USERNAME**: Your Docker Hub username
2. **DOCKER_PASSWORD**: Your Docker Hub password or access token

### Optional Integrations
- **Codecov**: Sign up at codecov.io and link your repository for coverage tracking
- **Docker Hub**: Create a repository for your Docker images

## Coverage Requirements
- Overall coverage: 70% minimum
- Changed files coverage: 80% minimum

## Database Configuration
The workflows use MySQL 8.0 as a test database service with:
- Root password: `root`
- Database name: `restaurant_db`
- Port: `3306`

## Artifacts
The workflows generate and store the following artifacts:
- JAR files from builds
- Test reports
- Coverage reports
- OWASP security scan reports
- Dependency update reports

## Manual Triggers
You can manually trigger workflows from the GitHub Actions tab:
- Dependency Update Check: Can be triggered manually anytime
- Other workflows: Triggered automatically based on git events