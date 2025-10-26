import { useState } from 'react';
import { motion } from 'framer-motion';
import { Play, Copy, Check } from 'lucide-react';
import { executeAPI } from '@/api/api';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';

interface ApiEndpointProps {
  method: string;
  endpoint: string;
  description: string;
  parameters?: { name: string; type: string; required: boolean }[];
  bodyFields?: { name: string; type: string; required: boolean }[];
}

const ApiEndpoint = ({ method, endpoint, description, parameters, bodyFields }: ApiEndpointProps) => {
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState<any>(null);
  const [statusCode, setStatusCode] = useState<number | null>(null);
  const [params, setParams] = useState<Record<string, string>>({});
  const [bodyData, setBodyData] = useState<Record<string, string>>({});
  const [copied, setCopied] = useState(false);

  const getMethodColor = (method: string) => {
    switch (method.toUpperCase()) {
      case 'GET': return 'bg-green-500/20 text-green-600 dark:text-green-400';
      case 'POST': return 'bg-blue-500/20 text-blue-600 dark:text-blue-400';
      case 'PUT': return 'bg-yellow-500/20 text-yellow-600 dark:text-yellow-400';
      case 'DELETE': return 'bg-red-500/20 text-red-600 dark:text-red-400';
      default: return 'bg-gray-500/20 text-gray-600 dark:text-gray-400';
    }
  };

  const handleExecute = async () => {
    setLoading(true);
    setResponse(null);
    setStatusCode(null);

    try {
      let finalEndpoint = endpoint;
      
      // Replace path parameters
      if (parameters) {
        parameters.forEach(param => {
          if (params[param.name]) {
            finalEndpoint = finalEndpoint.replace(`{${param.name}}`, params[param.name]);
          }
        });
      }

      // Prepare request data
      let requestData = undefined;
      if (bodyFields && bodyFields.length > 0) {
        requestData = {};
        bodyFields.forEach(field => {
          if (bodyData[field.name]) {
            // Try to parse as JSON if it looks like an object/array
            try {
              requestData[field.name] = field.type === 'object' || field.type === 'array' 
                ? JSON.parse(bodyData[field.name]) 
                : bodyData[field.name];
            } catch {
              requestData[field.name] = bodyData[field.name];
            }
          }
        });
      }

      const result = await executeAPI(method, finalEndpoint, requestData);
      setResponse(result.data);
      setStatusCode(result.status);
      toast.success(`${method} ${finalEndpoint} - Success!`);
    } catch (error: any) {
      setResponse(error.response?.data || { error: error.message });
      setStatusCode(error.response?.status || 500);
      toast.error(`Error: ${error.response?.data?.message || error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const copyResponse = () => {
    navigator.clipboard.writeText(JSON.stringify(response, null, 2));
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
    toast.success('Response copied to clipboard!');
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card p-6 rounded-xl mb-4"
    >
      <div className="flex items-start justify-between mb-4">
        <div className="flex-1">
          <div className="flex items-center gap-3 mb-2">
            <span className={`px-3 py-1 rounded-full text-xs font-bold ${getMethodColor(method)}`}>
              {method}
            </span>
            <code className="text-sm font-mono text-foreground">{endpoint}</code>
          </div>
          <p className="text-sm text-muted-foreground">{description}</p>
        </div>
      </div>

      {/* Parameters */}
      {parameters && parameters.length > 0 && (
        <div className="mb-4">
          <Label className="mb-2 block text-sm font-semibold">Path Parameters</Label>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            {parameters.map(param => (
              <div key={param.name}>
                <Label className="text-xs text-muted-foreground">
                  {param.name} {param.required && <span className="text-red-500">*</span>}
                </Label>
                <Input
                  placeholder={`Enter ${param.name}`}
                  value={params[param.name] || ''}
                  onChange={(e) => setParams({ ...params, [param.name]: e.target.value })}
                  className="mt-1"
                />
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Body Fields */}
      {bodyFields && bodyFields.length > 0 && (
        <div className="mb-4">
          <Label className="mb-2 block text-sm font-semibold">Request Body</Label>
          <div className="space-y-3">
            {bodyFields.map(field => (
              <div key={field.name}>
                <Label className="text-xs text-muted-foreground">
                  {field.name} ({field.type}) {field.required && <span className="text-red-500">*</span>}
                </Label>
                {field.type === 'object' || field.type === 'array' ? (
                  <Textarea
                    placeholder={`Enter ${field.name} as JSON`}
                    value={bodyData[field.name] || ''}
                    onChange={(e) => setBodyData({ ...bodyData, [field.name]: e.target.value })}
                    className="mt-1 font-mono text-sm"
                    rows={4}
                  />
                ) : (
                  <Input
                    placeholder={`Enter ${field.name}`}
                    value={bodyData[field.name] || ''}
                    onChange={(e) => setBodyData({ ...bodyData, [field.name]: e.target.value })}
                    className="mt-1"
                    type={field.type === 'number' ? 'number' : 'text'}
                  />
                )}
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Execute Button */}
      <Button
        onClick={handleExecute}
        disabled={loading}
        className="w-full gradient-primary text-white font-semibold"
      >
        {loading ? (
          <span className="flex items-center gap-2">
            <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
            Executing...
          </span>
        ) : (
          <span className="flex items-center gap-2">
            <Play className="w-4 h-4" />
            Execute
          </span>
        )}
      </Button>

      {/* Response */}
      {response !== null && (
        <motion.div
          initial={{ opacity: 0, height: 0 }}
          animate={{ opacity: 1, height: 'auto' }}
          className="mt-4"
        >
          <div className="flex items-center justify-between mb-2">
            <Label className="text-sm font-semibold">Response</Label>
            <div className="flex items-center gap-2">
              <span className={`text-xs font-mono px-2 py-1 rounded ${
                statusCode && statusCode < 300 ? 'bg-green-500/20 text-green-600' : 'bg-red-500/20 text-red-600'
              }`}>
                Status: {statusCode}
              </span>
              <Button
                variant="ghost"
                size="sm"
                onClick={copyResponse}
                className="h-8"
              >
                {copied ? <Check className="w-4 h-4" /> : <Copy className="w-4 h-4" />}
              </Button>
            </div>
          </div>
          <pre className="bg-muted/50 p-4 rounded-lg overflow-x-auto text-xs font-mono">
            {JSON.stringify(response, null, 2)}
          </pre>
        </motion.div>
      )}
    </motion.div>
  );
};

export default ApiEndpoint;
