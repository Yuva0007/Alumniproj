import React, { useState } from 'react';

export default function AlumniSearch({ onSearch }) {
  const [query, setQuery] = useState('');
  const [yearOption, setYearOption] = useState('All');
  const [mentorshipAvailable, setMentorshipAvailable] = useState(false);

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
    onSearch({ query: e.target.value });
  };

  const handleYearChange = (e) => {
    setYearOption(e.target.value);
    if (e.target.value === 'All') {
      onSearch({ startYear: null, endYear: null });
    } else {
      const [start, end] = e.target.value.split('-').map(Number);
      onSearch({ startYear: start, endYear: end });
    }
  };

  const handleMentorshipChange = (e) => {
    setMentorshipAvailable(e.target.checked);
    onSearch({ mentorshipAvailable: e.target.checked });
  };

  return (
    <div>
      <input
        data-testid="search-input"
        name="search"
        value={query}
        onChange={handleQueryChange}
      />
      <select
        data-testid="year-select"
        name="yearOption"
        value={yearOption}
        onChange={handleYearChange}
      >
        <option value="All">All</option>
        <option value="2015-2019">2015-2019</option>
        <option value="2020-2021">2020-2021</option>
      </select>
      <label>
        <input
          type="checkbox"
          data-testid="mentorship-checkbox"
          checked={mentorshipAvailable}
          onChange={handleMentorshipChange}
        />
        Available for mentorship
      </label>
    </div>
  );
}
