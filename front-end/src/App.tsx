import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { TooltipProvider } from "@/components/ui/tooltip";
import { Toaster as ShadToaster } from "@/components/ui/toaster"; // ✅ renamed to avoid conflict
import { Toaster as Sonner } from "@/components/ui/sonner";
import { AuthProvider } from "./context/AuthContext";
import { ThemeProvider } from "./context/ThemeContext";
import ProtectedRoute from "./components/ProtectedRoute";

// 🔹 Pages & Components
import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./pages/Home";
import ApiExplorer from "./components/ApiExplorer";
import NotFound from "./pages/NotFound";

// 🆕 Added pages
import AddStudent from "./pages/AddStudent";
import Predict from "./pages/Predict";
import Report from "./pages/Report";

// ✅ Create React Query client
const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      {/* ✅ Use both toasters without naming conflict */}
      <ShadToaster />
      <Sonner position="top-right" />
      <BrowserRouter>
        <ThemeProvider>
          <AuthProvider>
            <Routes>
              {/* Redirect to login by default */}
              <Route path="/" element={<Navigate to="/login" replace />} />

              {/* Public routes */}
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />

              {/* Protected routes */}
              <Route
                path="/dashboard"
                element={
                  <ProtectedRoute>
                    <Home />
                  </ProtectedRoute>
                }
              />

              {/* 🆕 Added pages */}
              <Route
                path="/add-student"
                element={
                  <ProtectedRoute>
                    <AddStudent />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/predict"
                element={
                  <ProtectedRoute>
                    <Predict />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/report"
                element={
                  <ProtectedRoute>
                    <Report />
                  </ProtectedRoute>
                }
              />

              {/* Optional: API Explorer tool */}
              <Route
                path="/api-explorer"
                element={
                  <ProtectedRoute>
                    <ApiExplorer />
                  </ProtectedRoute>
                }
              />

              {/* 404 Page */}
              <Route path="*" element={<NotFound />} />
            </Routes>
          </AuthProvider>
        </ThemeProvider>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
