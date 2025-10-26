import os
import sys
from flask import Flask, request, jsonify
import joblib
import numpy as np

# 👇 Ensure Python can import from src/
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'src')))

# ✅ Import helper modules
from feedback_generator import generate_feedback
from recommender_engine import recommend_resources

app = Flask(__name__)

# ✅ Load ML model and scaler
try:
    model = joblib.load(os.path.join('models', 'student_performance_model.pkl'))
    scaler = joblib.load(os.path.join('models', 'scaler.pkl'))
    print("✅ Model and Scaler loaded successfully.")
except Exception as e:
    print("❌ Failed to load model or scaler:", e)
    model, scaler = None, None


@app.route('/health', methods=['GET'])
def health():
    """Simple health check endpoint"""
    return jsonify({'status': 'OK', 'message': 'ML API Running Successfully'}), 200


@app.route('/predict', methods=['POST'])
def predict():
    """
    Endpoint to predict student performance.
    Accepts either:
    {
        "features": [attendance, avgMarks, studyHours]
    }
    OR
    {
        "attendance": 85,
        "avgMarks": 78,
        "studyHours": 3
    }
    """
    try:
        if not model or not scaler:
            return jsonify({'error': 'Model or scaler not loaded'}), 500

        body = request.get_json(force=True)
        if not body:
            return jsonify({'error': 'Empty request body'}), 400

        # ✅ Handle both payload formats
        if "features" in body:
            features = body["features"]
        else:
            features = [
                body.get("attendance", 0),
                body.get("avgMarks", 0),
                body.get("studyHours", 0)
            ]

        # ✅ Validate feature list
        if not isinstance(features, (list, tuple)) or len(features) < 3:
            return jsonify({'error': 'Invalid or incomplete features'}), 400

        attendance = features[0]

        # ✅ Scale and predict
        scaled = scaler.transform([features])
        prediction = model.predict(scaled)[0]
        predicted_score = round(float(prediction), 2)

        # ✅ Construct response
        result = {
            "prediction": predicted_score,
            "feedback": generate_feedback(predicted_score, attendance),
            "resources": recommend_resources("Math")
        }

        return jsonify(result), 200

    except Exception as e:
        import traceback
        print("❌ Exception in /predict:", traceback.format_exc())
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
