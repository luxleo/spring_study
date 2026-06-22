import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import axios from 'axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';

const googleUrl = "https://accounts.google.com/o/oauth2/auth"
const googleClientId = "989127917045-2s8qeevgm295vouvtb7rge7oengeg6b4.apps.googleusercontent.com"
const googleRedirectUrl = "http://localhost:3000/oauth/google/redirect"
const googleScope = "openid profile email"
const googleResponseType = "code"

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/member/doLogin', {
        email,
        password,
      });
      localStorage.setItem('token', response.data.token);
      alert('Login successful!');
      navigate('/');
    } catch (error) {
      console.error('Login error:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  const handleGoogleLogin = () => {
    const auth_uri = `${googleUrl}?client_id=${googleClientId}&redirect_uri=${googleRedirectUrl}&response_type=${googleResponseType}&scope=${googleScope}`
    window.location.href = auth_uri
  };

  return (
    <div className="flex items-center justify-center min-h-screen">
      <Card className="w-[400px]">
        <CardHeader>
          <CardTitle>Login</CardTitle>
          <CardDescription>Enter your email and password to login.</CardDescription>
        </CardHeader>
        <form onSubmit={handleLogin}>
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
            <Button type="submit" className="w-full">Login</Button>
            <Button type="button" variant="outline" className="w-full" onClick={handleGoogleLogin}>
              Login with Google
            </Button>
            <Button variant="link" onClick={() => navigate('/create')}>
              Don't have an account? Sign Up
            </Button>
          </CardFooter>
        </form>
      </Card>
    </div>
  );
};

export default LoginPage;
