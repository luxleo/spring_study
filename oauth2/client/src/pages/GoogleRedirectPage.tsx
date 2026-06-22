import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router';

const GoogleRedirectPage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const token = params.get('code');

    if (token) {
      localStorage.setItem('token', token);
      alert('Google Login successful!');
      navigate('/');
    } else {
      alert('Google Login failed.');
      navigate('/login');
    }
  }, [location, navigate]);

  return (
    <div className="flex items-center justify-center min-h-screen">
      <p>Redirecting...</p>
    </div>
  );
};

export default GoogleRedirectPage;
