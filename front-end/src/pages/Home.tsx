import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { Users, TrendingUp, Award, BookOpen } from "lucide-react";
import {
  LineChart,
  Line,
  BarChart,
  Bar,
  PieChart,
  Pie,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import Navbar from "@/components/Navbar";
import Sidebar from "@/components/Sidebar";
import StatsCard from "@/components/StatsCard";
import api from "@/api/api";
import { useAuth } from "@/context/AuthContext";
import { useNavigate } from "react-router-dom"; // ✅ Added

const Home = () => {
  const { user } = useAuth();
  const navigate = useNavigate(); // ✅ Added navigation hook
  const [students, setStudents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    try {
      const response = await api.get("/students");
      setStudents(response.data);
    } catch (error) {
      console.error("Failed to fetch students", error);
    } finally {
      setLoading(false);
    }
  };

  // Mock data for charts
  const trendData = [
    { month: "Jan", marks: 65 },
    { month: "Feb", marks: 72 },
    { month: "Mar", marks: 68 },
    { month: "Apr", marks: 78 },
    { month: "May", marks: 82 },
    { month: "Jun", marks: 85 },
  ];

  const attendanceData = [
    { subject: "Math", attendance: 85 },
    { subject: "Science", attendance: 92 },
    { subject: "English", attendance: 78 },
    { subject: "History", attendance: 88 },
  ];

  const predictionData = [
    { name: "Excellent", value: 35, color: "#10b981" },
    { name: "Good", value: 45, color: "#3b82f6" },
    { name: "Average", value: 15, color: "#f59e0b" },
    { name: "Poor", value: 5, color: "#ef4444" },
  ];

  const statsCards = [
    {
      title: "Total Students",
      value: students.length.toString(),
      icon: Users,
      gradient: "from-blue-500 to-purple-600",
    },
    {
      title: "Avg Attendance",
      value: "86%",
      icon: TrendingUp,
      gradient: "from-green-500 to-teal-600",
    },
    {
      title: "Success Rate",
      value: "92%",
      icon: Award,
      gradient: "from-orange-500 to-pink-600",
    },
    {
      title: "Active Subjects",
      value: "12",
      icon: BookOpen,
      gradient: "from-indigo-500 to-blue-600",
    },
  ];

  return (
    <div className="flex min-h-screen bg-background">
      <Sidebar />

      <main className="flex-1 overflow-y-auto">
        <div className="container mx-auto p-6 space-y-6">
          <Navbar />

          {/* Hero Section */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="glass-card p-8 bg-gradient-primary"
          >
            <h2 className="text-3xl font-bold text-white mb-2">
              Welcome back, {user?.username}! 👋
            </h2>
            <p className="text-white/80">
              Ready to analyze your students' performance?
            </p>
          </motion.div>

          {/* Stats Cards */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {statsCards.map((stat, index) => (
              <motion.div
                key={stat.title}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
              >
                <StatsCard {...stat} />
              </motion.div>
            ))}
          </div>

          {/* Analytics Section */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {/* Line Chart */}
            <motion.div
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.4 }}
              className="glass-card p-6"
            >
              <h3 className="text-xl font-semibold text-foreground mb-4">
                Performance Trend
              </h3>
              <ResponsiveContainer width="100%" height={250}>
                <LineChart data={trendData}>
                  <CartesianGrid
                    strokeDasharray="3 3"
                    stroke="hsl(var(--muted))"
                  />
                  <XAxis dataKey="month" stroke="hsl(var(--muted-foreground))" />
                  <YAxis stroke="hsl(var(--muted-foreground))" />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "hsl(var(--card))",
                      border: "1px solid hsl(var(--border))",
                    }}
                  />
                  <Line
                    type="monotone"
                    dataKey="marks"
                    stroke="hsl(var(--primary))"
                    strokeWidth={3}
                  />
                </LineChart>
              </ResponsiveContainer>
            </motion.div>

            {/* Bar Chart */}
            <motion.div
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.5 }}
              className="glass-card p-6"
            >
              <h3 className="text-xl font-semibold text-foreground mb-4">
                Average Attendance
              </h3>
              <ResponsiveContainer width="100%" height={250}>
                <BarChart data={attendanceData}>
                  <CartesianGrid
                    strokeDasharray="3 3"
                    stroke="hsl(var(--muted))"
                  />
                  <XAxis
                    dataKey="subject"
                    stroke="hsl(var(--muted-foreground))"
                  />
                  <YAxis stroke="hsl(var(--muted-foreground))" />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "hsl(var(--card))",
                      border: "1px solid hsl(var(--border))",
                    }}
                  />
                  <Bar
                    dataKey="attendance"
                    fill="hsl(var(--primary))"
                    radius={[8, 8, 0, 0]}
                  />
                </BarChart>
              </ResponsiveContainer>
            </motion.div>

            {/* Pie Chart */}
            <motion.div
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.6 }}
              className="glass-card p-6"
            >
              <h3 className="text-xl font-semibold text-foreground mb-4">
                Prediction Distribution
              </h3>
              <ResponsiveContainer width="100%" height={250}>
                <PieChart>
                  <Pie
                    data={predictionData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={({ name, percent }) =>
                      `${name} ${(percent * 100).toFixed(0)}%`
                    }
                    outerRadius={80}
                    dataKey="value"
                  >
                    {predictionData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </motion.div>

            {/* Quick Stats */}
            <motion.div
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.7 }}
              className="glass-card p-6"
            >
              <h3 className="text-xl font-semibold text-foreground mb-4">
                Quick Stats
              </h3>
              <div className="space-y-4">
                <div className="flex justify-between items-center p-3 bg-muted rounded-lg">
                  <span className="text-muted-foreground">Last Login</span>
                  <span className="font-semibold text-foreground">
                    {new Date().toLocaleDateString()}
                  </span>
                </div>
                <div className="flex justify-between items-center p-3 bg-muted rounded-lg">
                  <span className="text-muted-foreground">Auth Token</span>
                  <span className="text-green-500 font-semibold">✅ Active</span>
                </div>
                <div className="flex justify-between items-center p-3 bg-muted rounded-lg">
                  <span className="text-muted-foreground">Total Predictions</span>
                  <span className="font-semibold text-foreground">247</span>
                </div>
              </div>
            </motion.div>
          </div>

          {/* Action Cards */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Add Student */}
            <motion.div
              whileHover={{ scale: 1.05 }}
              className="glass-card p-6 cursor-pointer bg-gradient-to-br from-blue-500/10 to-purple-600/10 border-primary/20"
              onClick={() => navigate("/add-student")} // ✅ Click event
            >
              <div className="text-4xl mb-3">➕</div>
              <h4 className="text-xl font-semibold text-foreground mb-2">
                Add New Student
              </h4>
              <p className="text-muted-foreground">
                Register a new student in the system
              </p>
            </motion.div>

            {/* Predict Performance */}
            <motion.div
              whileHover={{ scale: 1.05 }}
              className="glass-card p-6 cursor-pointer bg-gradient-to-br from-green-500/10 to-teal-600/10 border-primary/20"
              onClick={() => navigate("/predict")} // ✅ Click event
            >
              <div className="text-4xl mb-3">🔍</div>
              <h4 className="text-xl font-semibold text-foreground mb-2">
                Predict Performance
              </h4>
              <p className="text-muted-foreground">
                Get AI-powered performance predictions
              </p>
            </motion.div>

            {/* View Detailed Report */}
            <motion.div
              whileHover={{ scale: 1.05 }}
              className="glass-card p-6 cursor-pointer bg-gradient-to-br from-orange-500/10 to-pink-600/10 border-primary/20"
              onClick={() => navigate("/report")} // ✅ Click event
            >
              <div className="text-4xl mb-3">📊</div>
              <h4 className="text-xl font-semibold text-foreground mb-2">
                View Detailed Report
              </h4>
              <p className="text-muted-foreground">
                Access comprehensive analytics
              </p>
            </motion.div>
          </div>

          {/* Footer */}
          <motion.footer
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.8 }}
            className="text-center py-6 text-muted-foreground"
          >
            Powered by <strong>StudyPredict API</strong> | Auth Token:{" "}
            <span className="text-green-500">Active ✅</span>
          </motion.footer>
        </div>
      </main>
    </div>
  );
};

export default Home;
