import { motion } from 'framer-motion';
import { LucideIcon } from 'lucide-react';

interface StatsCardProps {
  title: string;
  value: string | number;
  icon: LucideIcon;
  gradient: string;
}

const StatsCard = ({ title, value, icon: Icon, gradient }: StatsCardProps) => {
  return (
    <motion.div
      whileHover={{ y: -5 }}
      className="glass-card p-6 relative overflow-hidden"
    >
      <div className={`absolute top-0 right-0 w-24 h-24 bg-gradient-to-br ${gradient} opacity-10 rounded-full -mr-12 -mt-12`} />
      
      <div className="relative">
        <div className="flex items-center justify-between mb-4">
          <div className={`p-3 rounded-xl bg-gradient-to-br ${gradient}`}>
            <Icon className="w-6 h-6 text-white" />
          </div>
        </div>
        
        <h3 className="text-muted-foreground text-sm font-medium mb-1">{title}</h3>
        <p className="text-3xl font-bold text-foreground">{value}</p>
      </div>
    </motion.div>
  );
};

export default StatsCard;
