export const metadata = {
  title: "bills",
  description: "Phan Family Expense Tracker",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body style={{ margin: 0, padding: 0, overflowX: `hidden` }}>
        {children}
      </body>
    </html>
  );
}
