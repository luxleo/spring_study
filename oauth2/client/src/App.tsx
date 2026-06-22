import { Routes, Route, useNavigate } from 'react-router'
import LoginPage from './pages/LoginPage'
import CreatePage from './pages/CreatePage'
import GoogleRedirectPage from './pages/GoogleRedirectPage'
import { Button } from './components/ui/button'
import './App.css'

function App() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.reload();
  };

  return (
    <Routes>
      <Route path="/" element={
        <div className="flex flex-col items-center justify-center min-h-screen">
          <h1 className="text-4xl font-bold mb-8">Welcome to OAuth2 Practice</h1>
          
          <div className="flex gap-4">
            {token ? (
              <Button 
                variant="destructive"
                onClick={handleLogout}
              >
                로그아웃
              </Button>
            ) : (
              <>
                <Button onClick={() => navigate('/login')}>
                  로그인
                </Button>
                <Button variant="outline" onClick={() => navigate('/create')}>
                  회원가입
                </Button>
              </>
            )}
          </div>
          
          {token && <p className="mt-4 text-green-600 font-medium">You are logged in!</p>}
        </div>
      } />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/create" element={<CreatePage />} />
      <Route path="/oauth/google/redirect" element={<GoogleRedirectPage />} />
    </Routes>
  )
}

export default App
