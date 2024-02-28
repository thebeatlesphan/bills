import Navbar from "./components/navbar/Navbar";

export const metadata = {
  title: "bills",
  description: "Phan Family Expense Tracker",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        {children}
      </body>
    </html>
  );
}
