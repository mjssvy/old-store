package com.project.shopapp.service;

import com.project.shopapp.dtos.CommentDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Comment;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.User;
import com.project.shopapp.services.comment.CommentService;
import com.project.shopapp.repositories.CommentRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.comment.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentDTO commentDTO;
    private User user;
    private Product product;
    private Comment comment;

    @BeforeEach
    void setUp() {
        commentDTO = new CommentDTO();
        commentDTO.setUserId(1L);
        commentDTO.setProductId(1L);
        commentDTO.setContent("Great product!");

        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        comment = Comment.builder()
                .user(user)
                .product(product)
                .content(commentDTO.getContent())
                .build();
        comment.setId(1L);
    }

    @Test
    void insertComment_Success() {
        when(userRepository.findById(commentDTO.getUserId())).thenReturn(Optional.of(user));
        when(productRepository.findById(commentDTO.getProductId())).thenReturn(Optional.of(product));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment newComment = commentService.insertComment(commentDTO);

        assertNotNull(newComment);
        assertEquals(commentDTO.getContent(), newComment.getContent());
        verify(userRepository).findById(commentDTO.getUserId());
        verify(productRepository).findById(commentDTO.getProductId());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void insertComment_UserOrProductNotFound() {
        when(userRepository.findById(commentDTO.getUserId())).thenReturn(Optional.empty());
        when(productRepository.findById(commentDTO.getProductId())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.insertComment(commentDTO);
        });

        assertEquals("User or product not found", exception.getMessage());
    }

    @Test
    void deleteComment_Success() {
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository).deleteById(1L);
    }

    @Test
    void updateComment_Success() throws DataNotFoundException {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.updateComment(1L, commentDTO);

        verify(commentRepository).findById(1L);
        verify(commentRepository).save(any(Comment.class));
        assertEquals(commentDTO.getContent(), comment.getContent());
    }

    @Test
    void updateComment_CommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            commentService.updateComment(1L, commentDTO);
        });

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void getCommentsByUserAndProduct_Success() {
        when(commentRepository.findByUserIdAndProductId(1L, 1L)).thenReturn(Collections.singletonList(comment));

        List<CommentResponse> commentResponses = commentService.getCommentsByUserAndProduct(1L, 1L);

        assertNotNull(commentResponses);
        assertFalse(commentResponses.isEmpty());
        assertEquals(1, commentResponses.size());
        verify(commentRepository).findByUserIdAndProductId(1L, 1L);
    }

    @Test
    void getCommentsByProduct_Success() {
        when(commentRepository.findByProductId(1L)).thenReturn(Collections.singletonList(comment));

        List<CommentResponse> commentResponses = commentService.getCommentsByProduct(1L);

        assertNotNull(commentResponses);
        assertFalse(commentResponses.isEmpty());
        assertEquals(1, commentResponses.size());
        verify(commentRepository).findByProductId(1L);
    }
}
