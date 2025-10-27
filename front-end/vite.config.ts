import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: "::", // allows access from any local network device
    port: 2025, // your frontend development port
    proxy: {
      "/api": {
        target: "http://localhost:8080", // backend server URL
        changeOrigin: true,
        secure: false,
      },
    },
  },

  plugins: [react()],

  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"), // use '@' for cleaner imports
    },
  },

  build: {
    outDir: "dist",
    sourcemap: true, // helps debug in production
  },
});
