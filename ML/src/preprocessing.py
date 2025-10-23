import pandas as pd
from sklearn.preprocessing import StandardScaler, LabelEncoder

def load_and_preprocess_data(filepath):
    """
    Loads and preprocesses student performance data.
    """
    df = pd.read_csv(filepath)

    # Example columns: student_id, attendance, study_hours, assignments_score, previous_score, final_score, pass_fail
    df.fillna(df.mean(numeric_only=True), inplace=True)

    le = None
    if 'pass_fail' in df.columns:
        le = LabelEncoder()
        df['pass_fail'] = le.fit_transform(df['pass_fail'])

    # Select features and target
    feature_cols = ['attendance', 'study_hours', 'assignments_score', 'previous_score']
    X = df[feature_cols]
    y = df['pass_fail'] if 'pass_fail' in df.columns else df['final_score']

    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    return X_scaled, y, scaler, le
