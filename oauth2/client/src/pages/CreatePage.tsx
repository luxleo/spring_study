import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import axios from 'axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';

const CreatePage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/member/create', {
        email,
        password,
      });
      alert('Account created successfully!');
      navigate('/login');
    } catch (error) {
      console.error('Signup error:', error);
      alert('Failed to create account.');
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen">
      <Card className="w-[400px]">
        <CardHeader>
          <CardTitle>Create Account</CardTitle>
          <CardDescription>Enter your email and password to create an account.</CardDescription>
        </CardHeader>
        <form onSubmit={handleCreate}>
          <CardContent className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                placeholder="test@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
          </CardContent>
          <CardFooter className="flex flex-col space-y-2">
            <Button type="submit" className="w-full">Sign Up</Button>
            <Button variant="link" onClick={() => navigate('/login')}>
              Already have an account? Login
            </Button>
          </CardFooter>
        </form>
      </Card>
    </div>
  );
};

export default CreatePage;
