package com.sumy.gamestore.controller.main.api;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sumy.gamestore.dto.ResponseDto;
import com.sumy.gamestore.model.ReportList;
import com.sumy.gamestore.model.ReviewList;
import com.sumy.gamestore.service.ReportListService;
import com.sumy.gamestore.service.ReviewListService;

@RequiredArgsConstructor
@RestController
public class SingleProductApiController {

	private final ReviewListService reviewListService;
	private final ReportListService reportListService;
	
	@PutMapping("/sumy/game/review")
	public ResponseDto<Integer> updateReview(@RequestBody ReviewList reviewList){
		System.out.println(reviewList);
		ReviewList resultReview = reviewListService.findById(reviewList.getReviewId());
		resultReview.setReviewText(reviewList.getReviewText());
		resultReview.setReviewStarRating(reviewList.getReviewStarRating());
		resultReview.setReviewUpdateDate(LocalDateTime.now());
		
		reviewListService.update(resultReview);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/sumy/game/review")
	public ResponseDto<Integer> writeReview(@RequestBody ReviewList reviewList){
		System.out.println(reviewList);
		ReviewList inputReview = ReviewList.builder()
										   .reviewId(0)
										   .gameId(reviewList.getGameId())
										   .userId(reviewList.getUserId())
										   .reviewText(reviewList.getReviewText())
										   .reviewLikeCount(0)
										   .reviewReportCount(0)
										   .reviewStarRating(reviewList.getReviewStarRating())
										   .reviewWriteDate(LocalDateTime.now())
										   .reviewUpdateDate(LocalDateTime.now())
										   .build();
		
		reviewListService.save(inputReview);
										   
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	
	@PostMapping("/sumy/game/review/report")
	public ResponseDto<Integer> sendReport(@RequestBody ReportList reportList){
		System.out.println(reportList);
		
		ReportList inputReport = ReportList.builder()
										   .reportId(0)
										   .reportFromUserId(reportList.getReportFromUserId())
										   .reviewId(reportList.getReviewId())
										   .reportText(reportList.getReportText())
										   .reportWriteDate(LocalDateTime.now())
										   .reportReadYn(0)
										   .build();
										   
		reportListService.save(inputReport);
		ReviewList review = reviewListService.findById(reportList.getReviewId());
		review.setReviewReportCount(reportListService.getCountById(reportList.getReviewId()));
		reviewListService.update(review);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/sumy/game/review")
	public ResponseDto<Integer> deleteReview(@RequestBody ReviewList reviewList){
		System.out.println(reviewList);
										   
		reviewListService.delete(reviewList.getReviewId());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
