def recommend_resources(subject):
    """
    Suggests study resources based on subject area.
    """
    resources = {
        'Math': [
            '📘 Khan Academy – Algebra & Calculus',
            '📹 YouTube – Math Tricks and Problem Solving'
        ],
        'Science': [
            '🔬 CrashCourse – Physics, Chemistry, Biology',
            '🎓 NPTEL Lectures – Fundamentals of Science'
        ],
        'English': [
            '🗣️ BBC Learning English – Grammar and Vocabulary',
            '📖 Grammarly Blog – Writing Tips'
        ]
    }
    return resources.get(subject, ['📚 General study techniques and time management resources'])
