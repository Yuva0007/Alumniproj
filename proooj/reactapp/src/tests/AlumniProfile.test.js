import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import AlumniProfile from '../components/AlumniProfile';

describe('AlumniProfile', () => {
  const alumniData = {
    id: '1',
    name: 'John Doe',
    graduationYear: 2018,
    currentCompany: 'Tech Innovations Inc.',
    jobTitle: 'Software Engineer',
    skills: ['Java', 'React', 'Spring Boot'],
    availableForMentorship: true
  };

  it('State_renders all alumni details and mentorship badge', () => {
    render(<AlumniProfile alumniData={alumniData} />);
    expect(screen.getByTestId('alumni-name')).toHaveTextContent('John Doe');
    expect(screen.getByTestId('alumni-year')).toHaveTextContent('2018');
    expect(screen.getByTestId('alumni-company')).toHaveTextContent('Tech Innovations Inc.');
    expect(screen.getByTestId('alumni-job')).toHaveTextContent('Software Engineer');
    const skills = screen.getAllByTestId('alumni-skill');
    expect(skills.length).toBe(3);
    expect(skills[0]).toHaveTextContent('Java');
    expect(skills[1]).toHaveTextContent('React');
    expect(skills[2]).toHaveTextContent('Spring Boot');
    expect(screen.getByTestId('mentorship-badge')).toBeInTheDocument();
  });

  it('State_shows connection message when Connect is clicked', () => {
    render(<AlumniProfile alumniData={alumniData} />);
    fireEvent.click(screen.getByTestId('connect-btn'));
    expect(screen.getByTestId('connection-msg')).toHaveTextContent('Connection request sent to John Doe');
  });

  it('State_does not show mentorship badge when not available', () => {
    const noMentor = { ...alumniData, availableForMentorship: false };
    render(<AlumniProfile alumniData={noMentor} />);
    expect(screen.queryByTestId('mentorship-badge')).not.toBeInTheDocument();
  });
});
