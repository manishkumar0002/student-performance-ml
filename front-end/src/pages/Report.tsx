import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { studentAPI } from "@/api/api";
import { toast } from "sonner";

interface Student {
  id: number;
  name: string;
  gender?: string;
  attendance?: number;
  avgMarks?: number;
  finalPrediction?: string;
}

const Report = () => {
  const [students, setStudents] = useState<Student[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const data = await studentAPI.getAllStudents();
        setStudents(data);
      } catch (error) {
        console.error("Error fetching students:", error);
        toast.error("❌ Failed to load report.");
      } finally {
        setLoading(false);
      }
    };
    fetchStudents();
  }, []);

  if (loading) return <div className="p-6">Loading report...</div>;

  return (
    <motion.div
      className="container mx-auto p-6 glass-card mt-10"
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
    >
      <h2 className="text-2xl font-bold mb-6 text-foreground">Detailed Student Report</h2>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse border border-gray-300 text-sm">
          <thead className="bg-muted">
            <tr>
              <th className="border p-2">ID</th>
              <th className="border p-2">Name</th>
              <th className="border p-2">Gender</th>
              <th className="border p-2">Attendance</th>
              <th className="border p-2">Avg Marks</th>
              <th className="border p-2">Prediction</th>
            </tr>
          </thead>
          <tbody>
            {students.map((s) => (
              <tr key={s.id} className="text-center hover:bg-muted/50">
                <td className="border p-2">{s.id}</td>
                <td className="border p-2">{s.name}</td>
                <td className="border p-2">{s.gender || "-"}</td>
                <td className="border p-2">{s.attendance ?? "-"}</td>
                <td className="border p-2">{s.avgMarks ?? "-"}</td>
                <td className="border p-2">{s.finalPrediction ?? "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </motion.div>
  );
};

export default Report;
