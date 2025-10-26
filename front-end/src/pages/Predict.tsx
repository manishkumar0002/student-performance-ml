import { useState, FormEvent } from "react";
import { motion } from "framer-motion";
import { predictionAPI } from "@/api/api";
import { toast } from "sonner";

interface PredictionResponse {
  feedback: string;
  prediction: number;
  resources?: string[];
}

const Predict = () => {
  const [form, setForm] = useState({
    attendance: "",
    avgMarks: "",
    studyHours: "",
  });
  const [result, setResult] = useState<PredictionResponse | null>(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handlePredict = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const payload = {
        attendance: Number(form.attendance),
        avgMarks: Number(form.avgMarks),
        studyHours: Number(form.studyHours),
      };

      const response = await predictionAPI.predictPerformance(payload);
      setResult(response);
      toast.success("🎯 Prediction generated successfully!");
    } catch (error) {
      console.error("Prediction failed:", error);
      toast.error("❌ Failed to predict performance.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div
      className="container mx-auto p-6 glass-card mt-10"
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
    >
      <h2 className="text-2xl font-bold mb-6 text-foreground">
        Predict Student Performance
      </h2>

      <form onSubmit={handlePredict} className="space-y-4 max-w-md">
        <input
          type="number"
          name="attendance"
          placeholder="Attendance %"
          value={form.attendance}
          onChange={handleChange}
          className="w-full p-2 border rounded-lg bg-background"
          required
        />
        <input
          type="number"
          name="avgMarks"
          placeholder="Average Marks"
          value={form.avgMarks}
          onChange={handleChange}
          className="w-full p-2 border rounded-lg bg-background"
          required
        />
        <input
          type="number"
          name="studyHours"
          placeholder="Study Hours per Day"
          value={form.studyHours}
          onChange={handleChange}
          className="w-full p-2 border rounded-lg bg-background"
          required
        />

        <button
          type="submit"
          disabled={loading}
          className="px-4 py-2 bg-green-600 text-white rounded-lg hover:opacity-90"
        >
          {loading ? "Predicting..." : "Predict"}
        </button>
      </form>

      {result && (
        <div className="mt-8 bg-muted p-4 rounded-lg">
          <h3 className="text-lg font-semibold mb-2">Prediction Result</h3>
          <p className="text-muted-foreground mb-2">{result.feedback}</p>
          <p>
            <strong>Predicted Score:</strong> {result.prediction}
          </p>
          {result.resources && (
            <ul className="list-disc ml-6 mt-2 text-sm">
              {result.resources.map((r, i) => (
                <li key={i}>{r}</li>
              ))}
            </ul>
          )}
        </div>
      )}
    </motion.div>
  );
};

export default Predict;
