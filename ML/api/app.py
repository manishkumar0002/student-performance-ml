import sys
import os
from flask import Flask, request, jsonify
import joblib
import numpy as np

# Fix import path
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

from src.feedback_generator import generate_feedback
from src.recommender_engine import recommend_resources

app = Flask(__name__)

# ✅ Load trained model and scaler
model = joblib.load('models/student_performance_model.pkl')
scaler = joblib.load('models/scaler.pkl')


@app.route('/health', methods=['GET'])
def health():
    return jsonify({'status': 'OK', 'message': 'ML API Running Successfully'})


@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json.get('features') or []
        if not data:
            return jsonify({'error': 'Missing features in request body'}), 400

        attendance = data[0]  # Assuming first feature = attendance

        scaled = scaler.transform([data])
        prediction = model.predict(scaled)
        predicted_score = float(prediction[0])

        result = {
            'prediction': predicted_score,
            'feedback': generate_feedback(predicted_score, attendance),
            'resources': recommend_resources('Math')
        }

        return jsonify(result)

    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
