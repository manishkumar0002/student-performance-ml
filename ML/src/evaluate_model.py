import joblib
from sklearn.metrics import classification_report, confusion_matrix
from src.preprocessing import load_and_preprocess_data

def evaluate_model():
    model = joblib.load('models/student_performance_model.pkl')
    X, y, _, _ = load_and_preprocess_data('data/student_performance.csv')

    y_pred = model.predict(X)

    print("📊 Classification Report:")
    print(classification_report(y, y_pred))
    print("🔢 Confusion Matrix:")
    print(confusion_matrix(y, y_pred))

if __name__ == "__main__":
    evaluate_model()
