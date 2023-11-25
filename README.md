# Gaspay Domains Nexus Package Repository

## Overview

This project serves as a package repository deployed on Nexus. It allows you to manage and distribute packages.

## Features

- **Package Management:** Easily upload, store, and retrieve packages.
- **Access Control:** Configure Nexus security settings to control who can access and publish packages.
- **Integration with Maven/Gradle:** Seamless integration with build tools for dependency management.

## Prerequisites

Before you start, ensure you have the following:

- Java Development Kit (JDK) installed (version 17 or later)
- Maven or Gradle installed (depending on your build tool preference)
- Nexus Repository Manager installed and configured

## Getting Started

1. **Clone the Repository:**

If you are using Maven, run the following command:

```bash
   mvn clean install
```

If you are using Gradle, run the following command:

```bash
   gradle clean build
``` 

2. **Configure Nexus Repository:**
   - Open Nexus Repository Manager in your browser.
   - Configure a new repository to host your packages.