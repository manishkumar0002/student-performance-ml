import joblib
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from preprocessing import load_and_preprocess_data
import os

def train_and_save_model():
    print("🚀 Training started...")

    X, y, scaler, le = load_and_preprocess_data('data/student_performance.csv')

    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42
    )

    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    acc = accuracy_score(y_test, y_pred)

    os.makedirs("models", exist_ok=True)
    joblib.dump(model, "models/student_performance_model.pkl")
    joblib.dump(scaler, "models/scaler.pkl")

    print(f"✅ Model trained successfully with accuracy: {acc * 100:.2f}%")
    print("📁 Model and Scaler saved to /models folder.")

if __name__ == "__main__":
    train_and_save_model()
