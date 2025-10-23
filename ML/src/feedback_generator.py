def generate_feedback(predicted_score, attendance):
    """
    Generates feedback message based on predicted score and attendance.
    """
    feedback = []

    if attendance < 75:
        feedback.append("⚠️ Improve your attendance; it’s below 75%.")
    if predicted_score < 50:
        feedback.append("📚 Focus on weak subjects and attempt more practice tests.")
    if 50 <= predicted_score < 80:
        feedback.append("👍 Doing well, but there’s room for improvement.")
    if predicted_score >= 80:
        feedback.append("🔥 Excellent performance! Keep maintaining this consistency.")

    return " ".join(feedback)
