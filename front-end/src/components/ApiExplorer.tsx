import { useState } from 'react';
import { motion } from 'framer-motion';
import { Users, BookOpen, TrendingUp, Link as LinkIcon, Shield } from 'lucide-react';
import Sidebar from '@/components/Sidebar';
import Navbar from '@/components/Navbar';
import ApiEndpoint from '@/components/ApiEndpoint';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';

const ApiExplorer = () => {
  const [activeCategory, setActiveCategory] = useState('students');

  const apiCategories = [
    {
      id: 'students',
      name: 'Students',
      icon: Users,
      color: 'text-blue-500',
      endpoints: [
        {
          method: 'GET',
          endpoint: '/students',
          description: 'Get all students',
        },
        {
          method: 'GET',
          endpoint: '/students/{id}',
          description: 'Get student by ID',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
        {
          method: 'POST',
          endpoint: '/students',
          description: 'Create new student',
          bodyFields: [
            { name: 'name', type: 'string', required: true },
            { name: 'email', type: 'string', required: true },
            { name: 'enrollmentDate', type: 'string', required: false },
          ],
        },
        {
          method: 'PUT',
          endpoint: '/students/{id}',
          description: 'Update student',
          parameters: [{ name: 'id', type: 'string', required: true }],
          bodyFields: [
            { name: 'name', type: 'string', required: false },
            { name: 'email', type: 'string', required: false },
          ],
        },
        {
          method: 'DELETE',
          endpoint: '/students/{id}',
          description: 'Delete student',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
        {
          method: 'POST',
          endpoint: '/students/{id}/predict',
          description: 'Predict student performance',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
      ],
    },
    {
      id: 'subjects',
      name: 'Subjects',
      icon: BookOpen,
      color: 'text-purple-500',
      endpoints: [
        {
          method: 'GET',
          endpoint: '/subjects',
          description: 'Get all subjects',
        },
        {
          method: 'GET',
          endpoint: '/subjects/{id}',
          description: 'Get subject by ID',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
        {
          method: 'POST',
          endpoint: '/subjects',
          description: 'Create new subject',
          bodyFields: [
            { name: 'name', type: 'string', required: true },
            { name: 'code', type: 'string', required: true },
            { name: 'credits', type: 'number', required: true },
          ],
        },
        {
          method: 'PUT',
          endpoint: '/subjects/{id}',
          description: 'Update subject',
          parameters: [{ name: 'id', type: 'string', required: true }],
          bodyFields: [
            { name: 'name', type: 'string', required: false },
            { name: 'credits', type: 'number', required: false },
          ],
        },
        {
          method: 'DELETE',
          endpoint: '/subjects/{id}',
          description: 'Delete subject',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
      ],
    },
    {
      id: 'performance',
      name: 'Performance',
      icon: TrendingUp,
      color: 'text-green-500',
      endpoints: [
        {
          method: 'GET',
          endpoint: '/performance',
          description: 'Get all performance records',
        },
        {
          method: 'POST',
          endpoint: '/performance',
          description: 'Create performance record',
          bodyFields: [
            { name: 'studentId', type: 'string', required: true },
            { name: 'subjectId', type: 'string', required: true },
            { name: 'score', type: 'number', required: true },
            { name: 'grade', type: 'string', required: false },
          ],
        },
      ],
    },
    {
      id: 'references',
      name: 'References',
      icon: LinkIcon,
      color: 'text-orange-500',
      endpoints: [
        {
          method: 'GET',
          endpoint: '/references',
          description: 'Get all references',
        },
        {
          method: 'POST',
          endpoint: '/references',
          description: 'Create new reference',
          bodyFields: [
            { name: 'title', type: 'string', required: true },
            { name: 'url', type: 'string', required: true },
            { name: 'description', type: 'string', required: false },
          ],
        },
        {
          method: 'DELETE',
          endpoint: '/references/{id}',
          description: 'Delete reference',
          parameters: [{ name: 'id', type: 'string', required: true }],
        },
      ],
    },
    {
      id: 'auth',
      name: 'Authentication',
      icon: Shield,
      color: 'text-red-500',
      endpoints: [
        {
          method: 'POST',
          endpoint: '/auth/login',
          description: 'User login',
          bodyFields: [
            { name: 'username', type: 'string', required: true },
            { name: 'password', type: 'string', required: true },
          ],
        },
        {
          method: 'POST',
          endpoint: '/auth/register',
          description: 'User registration',
          bodyFields: [
            { name: 'username', type: 'string', required: true },
            { name: 'email', type: 'string', required: true },
            { name: 'password', type: 'string', required: true },
          ],
        },
      ],
    },
  ];

  return (
    <div className="flex min-h-screen bg-gradient-to-br from-blue-50 via-white to-cyan-50 dark:from-gray-900 dark:via-gray-800 dark:to-blue-950">
      <Sidebar />
      <div className="flex-1 flex flex-col">
        <Navbar />
        <main className="flex-1 p-6 overflow-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="max-w-6xl mx-auto"
          >
            <div className="mb-8">
              <h1 className="text-4xl font-bold bg-gradient-primary bg-clip-text text-transparent mb-2">
                API Explorer
              </h1>
              <p className="text-muted-foreground">
                Test all backend endpoints with JWT authentication
              </p>
              <div className="mt-4 glass-card p-4 rounded-xl">
                <p className="text-sm text-muted-foreground">
                  <span className="font-semibold text-foreground">Base URL:</span>{' '}
                  <code className="bg-muted/50 px-2 py-1 rounded">http://localhost:2025/api</code>
                </p>
                <p className="text-sm text-muted-foreground mt-2">
                  <span className="font-semibold text-foreground">Auth Token:</span>{' '}
                  <code className="bg-muted/50 px-2 py-1 rounded text-xs">
                    {localStorage.getItem('token') ? '✓ Active' : '✗ Not found'}
                  </code>
                </p>
              </div>
            </div>

            <Tabs value={activeCategory} onValueChange={setActiveCategory} className="w-full">
              <TabsList className="grid w-full grid-cols-5 mb-6">
                {apiCategories.map(category => {
                  const Icon = category.icon;
                  return (
                    <TabsTrigger key={category.id} value={category.id} className="flex items-center gap-2">
                      <Icon className={`w-4 h-4 ${category.color}`} />
                      <span className="hidden sm:inline">{category.name}</span>
                    </TabsTrigger>
                  );
                })}
              </TabsList>

              {apiCategories.map(category => (
                <TabsContent key={category.id} value={category.id}>
                  <div className="space-y-4">
                    {category.endpoints.map((endpoint, index) => (
                      <ApiEndpoint key={index} {...endpoint} />
                    ))}
                  </div>
                </TabsContent>
              ))}
            </Tabs>
          </motion.div>
        </main>
      </div>
    </div>
  );
};

export default ApiExplorer;
