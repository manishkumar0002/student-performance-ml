import shap
import joblib
import matplotlib.pyplot as plt

def generate_shap_explanation(sample_data):
    """
    Generates SHAP explanation and saves summary plot.
    """
    model = joblib.load('models/student_performance_model.pkl')
    explainer = shap.TreeExplainer(model)
    shap_values = explainer.shap_values(sample_data)

    shap.summary_plot(shap_values, sample_data, show=False)
    plt.savefig('models/shap_summary.png')
    print("✅ SHAP explanation saved at models/shap_summary.png")
