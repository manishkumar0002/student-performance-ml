import { Moon, Sun, User } from 'lucide-react';
import { useTheme } from '@/context/ThemeContext';
import { useAuth } from '@/context/AuthContext';
import { motion } from 'framer-motion';

const Navbar = () => {
  const { isDark, toggleTheme } = useTheme();
  const { user } = useAuth();

  return (
    <motion.header
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      className="glass-card px-6 py-4 mb-6"
    >
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-bold text-foreground">
          Welcome back, <span className="text-primary">{user?.username}</span>!
        </h1>

        <div className="flex items-center gap-4">
          <button
            onClick={toggleTheme}
            className="p-2 rounded-xl hover:bg-muted transition-colors"
            aria-label="Toggle theme"
          >
            {isDark ? (
              <Sun className="w-5 h-5 text-yellow-500" />
            ) : (
              <Moon className="w-5 h-5 text-primary" />
            )}
          </button>

          <div className="flex items-center gap-2 px-4 py-2 rounded-xl bg-muted">
            <User className="w-5 h-5 text-primary" />
            <span className="font-medium text-foreground">{user?.username}</span>
          </div>
        </div>
      </div>
    </motion.header>
  );
};

export default Navbar;
