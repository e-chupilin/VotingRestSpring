package by.ontravelsolutions.interfaces;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import by.ontravelsolutions.exceptions.VotingRestException;
import by.ontravelsolutions.model.Vote;

public interface VotingService {

	public ResponseEntity<String> createTheme(String name) throws VotingRestException;

	public List<String> getAllTheme();

	public ResponseEntity<String> createVote(String name, RequestEntity<String> requestEntity)
			throws VotingRestException;

	public String stopVote(int id) throws VotingRestException;

	public Vote getVote(int id) throws VotingRestException;

	public ResponseEntity<String> setChoice(int id, String name, String choice) throws VotingRestException;

	public int getVoteStatic(int id, String choiceString) throws VotingRestException;

}