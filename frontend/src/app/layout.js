export const metadata = {
  title: "bill",
  description: "Phan Family Expense Tracker",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        {children}
      </body>
    </html>
  );
}
