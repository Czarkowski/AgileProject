# 📌 Projekt Vue.js + Spring Boot (Kotlin)

---
## 📝 Opis
Projekt na Koale

---
## 🛠 Technologie

### Frontend:
- Java Script
- Vue.js
- Type Script

### Backend:
- Kotlin
- Spring Boot
- Gradle
- PostgreSQL 
- Spring Security

---
## ⚙️ Wymagania
- Java 21
- PostgreSQL
- Node.js

---
## 🚀 Instalacja i uruchomienie

### 1️⃣ Pobranie projektu

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/Czarkowski/AgileProject.git
   cd AgileProject
   ```

---
### 2️⃣ Uruchomienie frontend

1. Przejdź do katalogu frontend:
   ```bash
   cd front
   ```
   
2. Zainstaluj zależności:
   ```bash
   npm install
   ```
3. Uruchom aplikację w trybie deweloperskim:
   ```bash
   npm run dev
   ```
4. Otwórz przeglądarkę i przejdź do `http://localhost:5173/` / `http://local.mariusz.com:5173` (lub inny port, jeśli Vite go zmienił).

---
### 3️⃣ Uruchomienie backend
1. Przejdź do katalogu backend:
   ```bash
   cd WebApi
   ```
2. Zainstaluj zależności i skompiluj projekt:
   ```bash
   ./gradlew build
   ```
3. Uruchom serwer Spring Boot:
   ```bash
   ./gradlew bootRun
   ```
4. Domyślnie backend działa na `http://localhost:8080/`.


---
## 📂 Struktura projektu
```
├── front/
│   ├── src/
│   │   ├── components/      # Komponenty Vue
│   │   ├── api/             # Metody dostępowe do api wygenerowane na podstawie dokumentacji OpenApi 
│   │   ├── assets/          # Zasoby (obrazy, style, itp.)
│   │   ├── main.js          # Główny plik aplikacji
│   │   ├── App.vue          # Główny komponent
│   ├── public/              # Statyczne pliki
│   ├── package.json         # Konfiguracja npm
│   ├── vite.config.js       # Konfiguracja Vite
│
├── WebApi/
│   ├── src/main/kotlin/     # Kod źródłowy aplikacji backendowej
│   ├── src/main/resources/  # Pliki konfiguracyjne
│   ├── build.gradle.kts     # Konfiguracja Gradle
│
├── README.md                # Dokumentacja projektu

TODO
```

---
## ✍️ Autor TODO
 - Oliwia Paliwoda
 - Aleksanda Maciejak
 - Szymon Szkatulski 
 - Miłosz Gmerek
 - Cezary Tytko

---
## 📜 Licencja
[TODO]

