import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import AlumniSearch from '../components/AlumniSearch';

describe('AlumniSearch', () => {
  it('Form_calls onSearch with correct parameters on input change', () => {
    const onSearch = jest.fn();
    render(<AlumniSearch onSearch={onSearch} />);
    const input = screen.getByTestId('search-input');
    fireEvent.change(input, { target: { value: 'Java', name: 'search' } });
    expect(onSearch).toHaveBeenCalledWith(
      expect.objectContaining({ query: 'Java' })
    );
    const select = screen.getByTestId('year-select');
    fireEvent.change(select, { target: { value: '2015-2019', name: 'yearOption' } });
    expect(onSearch).toHaveBeenCalledWith(
      expect.objectContaining({ startYear: 2015, endYear: 2019 })
    );
    const checkbox = screen.getByTestId('mentorship-checkbox');
    fireEvent.click(checkbox);
    expect(onSearch).toHaveBeenCalledWith(
      expect.objectContaining({ mentorshipAvailable: true })
    );
  });

  it('State_renders with default values', () => {
    render(<AlumniSearch onSearch={() => {}} />);
  
    // Check that the input field is empty by default
    const input = screen.getByTestId('search-input');
    expect(input.value).toBe('');
  
    // Check that the select field has a default value (assuming it defaults to a specific year range, like 'All years')
    const select = screen.getByTestId('year-select');
    expect(select.value).toBe('All'); // Replace with your actual default value
  
    // Check that the checkbox is unchecked by default
    const checkbox = screen.getByTestId('mentorship-checkbox');
    expect(checkbox.checked).toBe(false);
  });
  

  it('State_renders input, select, and checkbox', () => {
    render(<AlumniSearch onSearch={() => {}} />);
    expect(screen.getByTestId('search-input')).toBeInTheDocument();
    expect(screen.getByTestId('year-select')).toBeInTheDocument();
    expect(screen.getByTestId('mentorship-checkbox')).toBeInTheDocument();
  });
});
