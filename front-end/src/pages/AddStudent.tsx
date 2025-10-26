import { useState, FormEvent } from "react";
import { motion } from "framer-motion";
import { studentAPI } from "@/api/api";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

const AddStudent = () => {
  const [formData, setFormData] = useState({
    name: "",
    age: "",
    gender: "",
    attendance: "",
    avgMarks: "",
  });

  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Convert numeric fields properly
      const payload = {
        ...formData,
        age: Number(formData.age),
        attendance: Number(formData.attendance),
        avgMarks: Number(formData.avgMarks),
      };

      await studentAPI.addStudent(payload);
      toast.success("✅ Student added successfully!");
      navigate("/students"); // redirect to list
    } catch (error) {
      console.error("Error adding student:", error);
      toast.error("❌ Failed to add student.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div
      className="container mx-auto p-6 glass-card mt-10 max-w-lg"
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
    >
      <h2 className="text-2xl font-bold mb-6 text-foreground">➕ Add New Student</h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Name */}
        <div>
          <label className="block text-sm font-medium mb-1">Name</label>
          <input
            type="text"
            name="name"
            placeholder="Enter student name"
            value={formData.name}
            onChange={handleChange}
            required
            className="w-full p-2 border rounded-lg bg-background"
          />
        </div>

        {/* Age */}
        <div>
          <label className="block text-sm font-medium mb-1">Age</label>
          <input
            type="number"
            name="age"
            placeholder="Enter student age"
            value={formData.age}
            onChange={handleChange}
            required
            className="w-full p-2 border rounded-lg bg-background"
          />
        </div>

        {/* Gender */}
        <div>
          <label className="block text-sm font-medium mb-1">Gender</label>
          <select
            name="gender"
            value={formData.gender}
            onChange={handleChange}
            required
            className="w-full p-2 border rounded-lg bg-background"
          >
            <option value="">Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
          </select>
        </div>

        {/* Attendance */}
        <div>
          <label className="block text-sm font-medium mb-1">Attendance (%)</label>
          <input
            type="number"
            name="attendance"
            placeholder="Enter attendance (0-100)"
            value={formData.attendance}
            onChange={handleChange}
            required
            min="0"
            max="100"
            className="w-full p-2 border rounded-lg bg-background"
          />
        </div>

        {/* Average Marks */}
        <div>
          <label className="block text-sm font-medium mb-1">Average Marks</label>
          <input
            type="number"
            name="avgMarks"
            placeholder="Enter average marks (0-100)"
            value={formData.avgMarks}
            onChange={handleChange}
            required
            min="0"
            max="100"
            className="w-full p-2 border rounded-lg bg-background"
          />
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          disabled={loading}
          className="w-full px-4 py-2 bg-primary text-white rounded-lg hover:opacity-90"
        >
          {loading ? "Adding..." : "Add Student"}
        </button>
      </form>
    </motion.div>
  );
};

export default AddStudent;
