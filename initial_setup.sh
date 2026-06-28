```bash
#!/bin/bash

echo "========================================"
echo "Installing DevOps Tools"
echo "========================================"

# Update package list
sudo apt update

# Install Java
echo "Installing Java..."
sudo apt install -y openjdk-21-jdk

# Install Maven
echo "Installing Maven..."
sudo apt install -y maven

# Install Docker
echo "Installing Docker..."
sudo apt install -y docker.io

# Enable and start Docker
sudo systemctl enable docker
sudo systemctl start docker

# Add current user to Docker group
sudo usermod -aG docker $USER

# Install Docker Compose Plugin
echo "Installing Docker Compose..."
sudo apt install -y docker-compose-v2

echo ""
echo "========================================"
echo "Installation Completed"
echo "========================================"

echo ""
echo "Installed Versions:"
java -version
mvn -version
docker --version
docker compose version

echo ""
echo "Please log out and log in again (or reboot) before using Docker without sudo."
```
