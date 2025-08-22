import React, { useState } from 'react';
import AlumniSearch from './components/AlumniSearch';
import AlumniList from './components/AlumniList';

function App() {
  const [searchParams, setSearchParams] = useState({});
  const alumni = [
    { id: '1', name: 'Alice', graduationYear: 2021, currentCompany: 'A', jobTitle: 'J1', skills: ['Java'], availableForMentorship: false },
    { id: '2', name: 'Bob', graduationYear: 2020, currentCompany: 'B', jobTitle: 'J2', skills: ['React'], availableForMentorship: true },
    { id: '3', name: 'Charlie', graduationYear: 2015, currentCompany: 'C', jobTitle: 'J3', skills: ['SQL'], availableForMentorship: false },
    { id: '4', name: 'Dan', graduationYear: 2019, currentCompany: 'D', jobTitle: 'J4', skills: ['Spring'], availableForMentorship: false },
    { id: '5', name: 'Eve', graduationYear: 2018, currentCompany: 'E', jobTitle: 'J5', skills: ['Python'], availableForMentorship: true },
    { id: '6', name: 'Frank', graduationYear: 2017, currentCompany: 'F', jobTitle: 'J6', skills: ['Go'], availableForMentorship: false },
  ];

  const filteredAlumni = alumni.filter((alumni) => {
    const matchesQuery = searchParams.query
      ? alumni.name.toLowerCase().includes(searchParams.query.toLowerCase()) ||
        alumni.skills.some((skill) => skill.toLowerCase().includes(searchParams.query.toLowerCase()))
      : true;
    const matchesYear = searchParams.startYear && searchParams.endYear
      ? alumni.graduationYear >= searchParams.startYear && alumni.graduationYear <= searchParams.endYear
      : true;
    const matchesMentorship = searchParams.mentorshipAvailable
      ? alumni.availableForMentorship
      : true;
    return matchesQuery && matchesYear && matchesMentorship;
  });

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Alumni Directory</h1>
      <AlumniSearch onSearch={setSearchParams} />
      <AlumniList alumniList={filteredAlumni} />
    </div>
  );
}

export default App;