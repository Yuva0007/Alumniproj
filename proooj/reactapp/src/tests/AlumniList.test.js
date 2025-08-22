import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import AlumniList from '../components/AlumniList';

describe('AlumniList', () => {
  const alumni = [
    { id: '1', name: 'Alice', graduationYear: 2021, currentCompany: 'A', jobTitle: 'J1', skills: ['Java'], availableForMentorship: false },
    { id: '2', name: 'Bob', graduationYear: 2020, currentCompany: 'B', jobTitle: 'J2', skills: ['React'], availableForMentorship: true },
    { id: '3', name: 'Charlie', graduationYear: 2015, currentCompany: 'C', jobTitle: 'J3', skills: ['SQL'], availableForMentorship: false }
  ];
  const alumni6 = alumni.concat([
    { id: '4', name: 'Dan', graduationYear: 2019, currentCompany: 'D', jobTitle: 'J4', skills: ['Spring'], availableForMentorship: false },
    { id: '5', name: 'Eve', graduationYear: 2018, currentCompany: 'E', jobTitle: 'J5', skills: ['Python'], availableForMentorship: true },
    { id: '6', name: 'Frank', graduationYear: 2017, currentCompany: 'F', jobTitle: 'J6', skills: ['Go'], availableForMentorship: false },
  ]);

  it('State_renders a list of alumni using AlumniProfile', () => {
    render(<AlumniList alumniList={alumni} />);
    expect(screen.getAllByTestId('alumni-profile').length).toBe(3);
  });

  it('State_shows empty state when alumniList is empty', () => {
    render(<AlumniList alumniList={[]} />);
    expect(screen.getByTestId('alumni-empty')).toHaveTextContent('No alumni found matching your search criteria');
  });

  it('State_shows pagination controls and pages if more than 5 alumni', () => {
    render(<AlumniList alumniList={alumni6} />);
    expect(screen.getAllByTestId('alumni-profile').length).toBe(5); // Page 1
    const nextBtn = screen.getByTestId('next-page-btn');
    expect(nextBtn).not.toBeDisabled();
    fireEvent.click(nextBtn);
    // Page 2
    expect(screen.getByTestId('pagination-info')).toHaveTextContent('Page 2 of 2');
    expect(screen.getAllByTestId('alumni-profile').length).toBe(1);
    const prevBtn = screen.getByTestId('prev-page-btn');
    expect(prevBtn).not.toBeDisabled();
    fireEvent.click(prevBtn);
    expect(screen.getByTestId('pagination-info')).toHaveTextContent('Page 1 of 2');
  });
});
