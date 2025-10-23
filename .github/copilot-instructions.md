This repository contains two main parts: a Spring Boot Java backend and a small Python ML service. The goal of these instructions is to help an AI coding agent be immediately productive by documenting the architecture, developer workflows, and project-specific patterns.

High-level architecture
- Backend: `Backend/studentperformance` — a Spring Boot application (Java 17, Maven). Main entry: `src/main/java/com/studentperformance/StudentperformanceApplication.java`. Primary config: `src/main/resources/application.properties`.
- ML: `ML/` — Python code (Flask API in `ML/api/app.py`) and model code in `ML/src/`. Trained artifacts are in `ML/models/` (`student_performance_model.pkl`, `scaler.pkl`). Data is under `ML/data/student_performance.csv`.

Why this structure
- The Java service is the canonical backend; ML code is kept separate under `ML/` and exposes a lightweight Flask API for predictions. Models are saved as joblib pickles in `ML/models/` and the Flask app loads them at startup.

Key developer workflows (concrete commands)
- Build/run backend (from repository root):
  - mvn build and run: change to `Backend/studentperformance` and run:
    - mvn -f Backend/studentperformance clean package
    - java -jar Backend/studentperformance/target/studentperformance-0.0.1-SNAPSHOT.jar
  - In development, use the provided wrappers: `./mvnw` (Linux/macOS) inside `Backend/studentperformance`.
- Run ML API (from `ML/` root):
  - Ensure Python 3.11+ or appropriate venv with dependencies from `ML/requirements.txt`.
  - From `ML/` run: python api/app.py  (app expects to be run from project root so relative paths to `models/` and `src/` resolve)
- Train model (from `ML/src`):
  - python src/train_model.py
  - Artifacts will be saved to `ML/models/`.

Project-specific conventions and patterns
- ML code layout: put reusable Python modules under `ML/src/` (e.g., `preprocessing.py`, `train_model.py`, `feedback_generator.py`). The Flask app `ML/api/app.py` modifies sys.path to import `src.*` — always run the Flask app from the `ML/` root so relative model paths work.
- Model artifacts are binary pickles in `ML/models/`. When editing serialization/loading, maintain the same filenames: `student_performance_model.pkl` and `scaler.pkl`.
- Java code uses Lombok (annotation processor configured in `pom.xml`) — IDEs must enable annotation processing to avoid missing getters/setters during dev.
- Database connector: `mysql-connector-j` is included but `application.properties` currently has no DB URL — search for environment-specific deployment docs before wiring DB credentials.

Integration points & boundaries
- The ML Flask API is a separate process — integration happens over HTTP (ML `/predict` endpoint). Example request body shape expected by `api/app.py`: {"features": [attendance, study_hours, assignments_score, previous_score, ...]} and response contains `prediction`, `feedback`, and `resources`.
- The backend does not currently call the ML API in the repo; if you add integration, keep ML service as an external dependency and call its `/predict` endpoint.

Files and examples to inspect when implementing changes
- Backend: `Backend/studentperformance/pom.xml`, `src/main/java/com/studentperformance/StudentperformanceApplication.java`, `src/main/resources/application.properties`, `HELP.md` in backend root for extra notes.
- ML: `ML/api/app.py` (Flask entry), `ML/src/preprocessing.py`, `ML/src/train_model.py`, `ML/src/feedback_generator.py`, `ML/src/recommender_engine.py`, `ML/models/` (pickles), `ML/data/student_performance.csv`.

Common pitfalls and quick fixes
- Running `ML/api/app.py` from inside `ML/api/` breaks imports and model path resolution — run from `ML/` root.
- If Lombok-generated methods appear missing in IDE, enable annotation processing or run `mvn -DskipTests package` to compile.
- If ML model files are missing, run `python ML/src/train_model.py` to regenerate them; this script writes to `ML/models/`.

When editing or adding features
- Small API changes in the Flask app: keep request/response JSON shapes backward compatible. Use try/except around model loading and return informative 5xx JSON when model artifacts are absent or corrupted.
- When modifying data preprocessing or model training, version model artifacts (e.g., add a `-v1` suffix) and update `api/app.py` to load the correct file.

Contact points in repo for reviewers
- Backend help: `Backend/studentperformance/HELP.md`.
- ML README is currently empty — add quick start steps and dependency pins when changing ML code.

If something's missing
- Ask the repo owner for expected DB config, intended ML endpoint integration, and preferred Python runtime. If tests or CI are added later, update this file with test commands and CI hooks.

Please review and tell me any specific areas you want expanded (CI, detailed class maps, or example integration tests).
